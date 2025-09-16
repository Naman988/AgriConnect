const express = require("express");
const admin = require("firebase-admin");
const functions = require("firebase-functions");

// Create an Express router to group our auth-related endpoints
const router = express.Router();
const db = admin.firestore();

/**
 * @api {post} /api/v1/auth/verify-otp Verify User's Firebase Auth Token
 * @apiName VerifyOTP
 * @apiGroup Auth
 *
 * @apiDescription This endpoint is called by the Android app immediately after a user
 * successfully logs in using Phone OTP via the Firebase SDK. It takes the user's
 * ID token, verifies it, and then creates a user profile in Firestore if one
 * doesn't already exist.
 *
 * @apiHeader {String} Authorization Bearer <Firebase_ID_Token>
 *
 * @apiSuccess {String} userId The user's unique ID (uid).
 * @apiSuccess {String} role The user's assigned role (e.g., 'FARMER').
 *
 * @apiError (401) Unauthorized The token is invalid or expired.
 * @apiError (500) InternalServerError Could not create the user profile.
 */
router.post("/verify-otp", async (req, res) => {
  // 1. Get the ID token from the Authorization header.
  // The original line used syntax (`?.`) that the linter didn't like.
  // This updated version is more compatible.
  const authHeader = req.headers.authorization;
  const idToken = authHeader && authHeader.split("Bearer ")[1];

  if (!idToken) {
    return res.status(401).send({error: "Unauthorized: No token provided."});
  }

  try {
    // 2. Verify the token using the Firebase Admin SDK.
    const decodedToken = await admin.auth().verifyIdToken(idToken);
    const uid = decodedToken.uid;
    const phone = decodedToken.phone_number;

    // 3. Check if a user profile already exists in Firestore.
    const userRef = db.collection("users").doc(uid);
    const userDoc = await userRef.get();

    let userData;

    if (userDoc.exists) {
      // 4a. If the user exists, return their existing data.
      functions.logger.log(`User ${uid} already exists.`);
      userData = userDoc.data();
    } else {
      // 4b. If the user is new, create a profile for them in Firestore.
      functions.logger.log(`Creating new user profile for ${uid}.`);
      userData = {
        userId: uid,
        phone: phone,
        role: "FARMER", // Assign a default role for all new sign-ups
        createdAt: admin.firestore.FieldValue.serverTimestamp(),
        updatedAt: admin.firestore.FieldValue.serverTimestamp(),
      };
      await userRef.set(userData);
    }

    // 5. Send the user's ID and role back to the app.
    res.status(200).send({
      userId: userData.userId,
      role: userData.role,
    });
  } catch (error) {
    functions.logger.error("Error verifying token:", error);
    res.status(401).send({error: "Unauthorized: Invalid token."});
  }
});

// We will add the /admin-login endpoint here later.

module.exports = router;

