import express from 'express';
import mongoose from 'mongoose';
import fs from 'fs';
import https from 'https';
import plantAPIRouter from './routes/api/plant';
import userAPIRouter from './routes/api/user';
import passport from 'passport';
import passportCreate from './config/passport';

const app = express();
const connectionString = process.env.DATABASE_URL || 'mongodb://localhost:27017/PlantreAPI';
const PORT = process.env.PORT || 3000;

async function main() {
  //Passport middleware
  app.use(passport.initialize());
  passportCreate(passport);

  //Parse json
  app.use(express.urlencoded({ extended: true }));
  app.use(express.json());

  app.use('/api/plant', plantAPIRouter);
  app.use('/api/user', userAPIRouter);
  // app.get('/', (req, res) => {
  //   res.send('Hello World!');
  //   console.log('Hello World');
  // });

  try {
    await mongoose.connect(connectionString);
  } catch {
    console.log('Could not connect to mongoose');
  }

  app.listen(PORT, () => {
    console.log('Sever started');
  });

  // https.createServer({ key: fs.readFileSync('priv_and_pub.key'), cert: fs.readFileSync('CA.crt') }, app).listen(PORT, () => {
  //   console.log('Server listening on port');
  // });
}

main();
