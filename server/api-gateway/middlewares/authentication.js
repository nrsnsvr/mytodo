const dotenv = require('dotenv')
const jwt = require('jsonwebtoken')

dotenv.config();
const ACCESS_TOKEN_SECRET = process.env.ACCESS_TOKEN_SECRET;

exports.authenticate = (req, res, next) => {
   console.log("=== Authentication Middleware ===");
   console.log("ACCESS_TOKEN_SECRET:", ACCESS_TOKEN_SECRET);
   
   const authHeader = req.headers['authorization'];
   console.log("authHeader:", authHeader);
   
   const token = authHeader?.split(' ')[1];
   console.log("token:", token);
   
   if (!token) {
      console.log("token not found")
      res.sendStatus(403);
      return;
   }

   jwt.verify(token, ACCESS_TOKEN_SECRET, (err, user) => {
      if (err) {
         console.log("token expired or invalid:", err.message)
         res.sendStatus(403);
         return;
      }
      console.log("in api gateway user verified: ", user)
      req.headers['x-user-id'] = user.id;
      req.user = user;
      next();
   });
};