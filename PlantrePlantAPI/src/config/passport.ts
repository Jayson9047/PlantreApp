import { Strategy as JwtStratgey, ExtractJwt, JwtFromRequestFunction, StrategyOptions } from 'passport-jwt';
import mongoose from 'mongoose';

import passport, { PassportStatic } from 'passport';
import UserModel from '../models/user';

const options: StrategyOptions = {
  jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
  secretOrKey: '6AC3C336E4094835293A3FED8A4B5FEDDE1B5E2626D9838FED50693BBA00AF0E',
};

const passportCreate = (passport: PassportStatic) => {
  passport.use(
    new JwtStratgey(options, async (jwt_payload, done) => {
      UserModel.findById(jwt_payload.id).then((user) => {
        if (user) {
          return done(null, user);
        } else {
          return done(null, false);
        }
      });
    })
  );
};
export default passportCreate;
