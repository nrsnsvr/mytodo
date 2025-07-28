const mongoose = require("mongoose");

const connectDB = async () => {
    try {
        console.log("Environment variables:", Object.keys(process.env).filter(key => key.includes('MONGODB')));
        console.log("MongoDB URI:", process.env.MONGODB_URI);
        console.log("MongoDB URI type:", typeof process.env.MONGODB_URI);
        await mongoose.connect(process.env.MONGODB_URI);
        console.log("Connected to MongoDB database successfully!")
    }catch (error) {
        console.log("Error connecting to MongoDB: ", error)
    }
}

module.exports = connectDB;