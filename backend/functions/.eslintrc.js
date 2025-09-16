module.exports = {
  root: true,
  env: {
    es6: true,
    node: true,
  },
  extends: [
    "eslint:recommended",
    "google",
  ],
  parserOptions: {
    "ecmaVersion": 2018,
  },
  rules: {
    "quotes": ["error", "double"],
    // This rule prevents errors on Windows machines.
    "linebreak-style": 0,
    // This rule allows for longer comment lines.
    "max-len": ["error", { "code": 120 }],
    // This allows for things like `express.Router()`.
    "new-cap": 0,
  },
};

