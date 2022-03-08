# android-demo

## Building and Packaging

1. Create a project in Firebase.
1. Click "Get Started" under App Distribution for your project and accept the terms. Before that,
   nothing works.
1. In the Firebase Console, navigate to Project settings -> Service accounts.
1. Follow the forms to create a new service account and JSON private key with Firebase Admin rights.
1. Add the service account credential file to the fastlane configuration.
1. Verify that you have followed https://docs.fastlane.tools/actions/supply/#setup to the letter. In
   particular, remember to click Grant Access for the newly created service account.

## Releasing

Before any app can be released using Fastlane (for testing or otherwise), one release must
be made manually.

In the Google Play console, release an app using the Closed testing Alpha track manually. This
requires uploading screenshots and filling in app metadata.
