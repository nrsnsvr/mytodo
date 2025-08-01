require('dotenv').config();
const express = require("express");
const cors = require('cors');
const { handleErrors } = require('./middlewares/ErrorHandler');
const toDoRoutes = require("./routes/ToDoRoutes")
const connectDB = require("./config/db");

const port = process.env.TODO_SERVICE_PORT || 9001
const app = express();

app.use(cors());
app.use(express.json())
app.use("/", toDoRoutes);

app.use("/test", (req, res) => {
   res.send("Todo service running successfully...")
})

// Error handler middleware should be last
app.use(handleErrors)

connectDB()
   .then(() => {
      app.listen(port, () => {
         console.log("Todo service running in port " + port)
      })
   })
   .catch((err) => {
      console.error('Failed to connect to MongoDB:', err);
   });