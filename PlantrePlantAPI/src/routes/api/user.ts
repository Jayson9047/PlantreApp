import { Router } from 'express';
import passport from 'passport';
import UserController from '../../controllers/UserController';

const userAPIRouter = Router();

userAPIRouter.get('/', passport.authenticate('jwt', { session: false }), UserController.activeUser);
userAPIRouter.post('/login', UserController.loginUser);
userAPIRouter.get('/logout', UserController.logoutUser);
userAPIRouter.post('/register', UserController.registerUser);
export default userAPIRouter;
