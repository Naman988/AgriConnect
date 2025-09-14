🌾 AgriConnect

AgriConnect is a blockchain + Firebase powered solution designed to bring transparency, trust, and efficiency to the agricultural supply chain. It ensures that farmers, processors, distributors, and consumers can track every step of a product’s journey with verified proofs of payment and transactions.

🚀 Features

✅ Farmer to Consumer Transparency – Track payments and transactions at every stage.

✅ Proof Storage – UPI payment screenshots stored securely on S3 (or GCS), with their hash on IPFS/Blockchain for immutability.

✅ Processor Integration – Handles crop processing (e.g., wheat → flour) with linked proofs.

✅ Admin Panel – Officials can verify and audit the entire timeline with all proofs.

✅ Consumer Portal – QR scan → see the full transparent journey of their purchase.

✅ Role-based Access – Separate logins for Farmers, Processors, Distributors, Admins, and Consumers.

🏗️ Architecture

Frontend (Mobile App):

Built using Android (Kotlin, Jetpack Compose).

Handles user logins, proof uploads, and QR-based tracking.

Backend & Blockchain:

Firebase – Authentication, Firestore DB, role management.

Storage – S3 or GCS for proof screenshots.

Blockchain (Hyperledger Fabric / IPFS) – Stores immutable proof hashes.

Admin Panel (App + Web):

Secure login with role-based access (Firebase).

Timeline view of all proofs and payments.

🔗 Workflow

Farmer uploads produce → Receives payment proof.

Processor processes crop → Uploads processing + payment proof.

Distributor pays processor → Uploads proof.

Consumer scans QR → Sees verified journey.

Admin verifies everything through the panel.

👥 Roles

Farmers: Sell produce, upload payment proofs.

Processors: Process produce, record proofs.

Distributors: Pay farmers/processors, upload proofs.

Admins: Audit and verify the entire timeline.

Consumers: Scan QR codes to check product history.

🛠️ Tech Stack

Frontend: Android (Kotlin, Jetpack Compose)

Backend: Firebase (Auth + Firestore)

Storage: AWS S3 / Google Cloud Storage

Blockchain: Hyperledger Fabric + IPFS

Version Control: Git + GitHub

📌 Roadmap

 Setup project repo and Firebase.

 Implement authentication (Farmer, Processor, Distributor, Admin).

 Integrate proof storage (S3/GCS + IPFS hash).

 Build consumer QR portal.

 Develop Admin Panel.

 Blockchain integration.

 Testing and Deployment.

📄 License

This project is licensed under the MIT License – feel free to use, modify, and share.
