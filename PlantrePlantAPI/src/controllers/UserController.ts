import validator from 'validator';
import { NextFunction, Request, Response } from 'express';
import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import UserModel from '../models/user';

class UserController {
  public registerUser(req: Request, res: Response) {
    if (!validator.isAlphanumeric(String(req.body.password)) || !validator.isEmail(String(req.body.email)))
      return res.status(400).json({ password: 'Password invalid', email: 'Email invalid' });
    UserModel.findOne({ email: req.body.email }).then((user) => {
      if (user) {
        return res.status(400).json({ email: 'Email already exists.' });
      } else {
        const newUser = new UserModel({
          username: req.body.username,
          email: req.body.email,
          password: req.body.password,
          firstname: req.body.firstname ? req.body.firstname : '',
          lastname: req.body.lastname ? req.body.lastname : '',
        });

        //Generate salt with bcrypt
        bcrypt.genSalt(10, (err, salt) => {
          //Usee generated salt to produce a hash for the password
          bcrypt.hash(newUser.password, salt, function (err, hash) {
            if (err) throw err;
            newUser.password = hash;
            newUser
              .save()
              .then((user) => res.json(user))
              .catch((err) => console.log(err));
          });
        });
      }
    });
  }

  public logoutUser(req: Request, res: Response) {
    res.json({ token: undefined });
  }

  public activeUser(req: Request, res: Response, next: NextFunction) {
    res.json({ hmm: 'OK' });
  }

  public loginUser(req: Request, res: Response) {
    //Create Route - Post
    //Check to see if user is available - if not 404 error
    //Comparte with bcrypt - if successful return our token

    UserModel.findOne({ email: req.body.email }).then((user) => {
      if (!user) return res.status(404).json({ email: 'User not found.' });

      bcrypt.compare(String(req.body.password), user.password, (err, same) => {
        if (same) {
          // User matched
          const payload = { id: user.id, email: user.email }; //JWT payload - info used to sign token
          // Sign token
          jwt.sign(
            payload,
            '6AC3C336E4094835293A3FED8A4B5FEDDE1B5E2626D9838FED50693BBA00AF0E',
            { expiresIn: 3600 },
            (err, token) => {
              res.json({ secret_token: token, user: user });
            }
          );
        } else {
          return res.status(400).json({ password: 'Incorrect password' });
        }
      });
    });
  }
}
export default new UserController();
