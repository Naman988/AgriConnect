const functions = require("firebase-functions");
const admin = require("firebase-admin");
const express = require("express");
const cors = require("cors");

// Initialize the Firebase Admin SDK.
admin.initializeApp();

const app = express();

// Use the CORS middleware.
app.use(cors({origin: true}));

// --- API Routes ---
// Import our authentication routes and tell our app to use them.
const authRoutes = require("./api/v1/auth");
app.use("/api/v1/auth", authRoutes);


// --- Main API Cloud Function ---
// Export a single Cloud Function to handle all requests.
exports.api = functions.https.onRequest(app);

