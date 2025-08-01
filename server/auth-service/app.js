require('dotenv').config();
const express = require("express");
const cors = require('cors');
const cookieParser = require("cookie-parser");
const { handleErrors } = require('./middlewares/ErrorHandler');
const authRoutes = require("./routes/AuthRoutes")
const connectDB = require("./config/db");

const port = process.env.AUTH_SERVICE_PORT || 9002
const app = express();

app.use(express.json())
app.use(cookieParser())
app.use(
   cors({
      origin: "http://localhost:5173",
      credentials: true,
   })
);

app.use("/", authRoutes);

app.use("/test", (req, res) => {
   res.send("Auth service running successfully...")
})

// Error handler middleware should be last
app.use(handleErrors)

connectDB()
   .then(() => {
      app.listen(port, () => {
         console.log("Auth service running in port " + port)
      })
   })
   .catch((err) => {
      console.error('Failed to connect to MongoDB:', err);
   });

