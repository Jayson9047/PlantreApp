import { Schema, model } from 'mongoose';

interface User {
  username: string;
  firstname: string;
  lastname: string;
  email: string;
  password: string;
  created_at: Date;
  updated_at: Date;
  deleted_at: Date;
}

const schema = new Schema<User>({
  username: { type: String, required: true },
  firstname: { type: String, required: false },
  lastname: { type: String, required: false },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  created_at: { type: Date, default: Date.now, required: true },
  updated_at: { type: Date, required: false },
  deleted_at: { type: Date, required: false },
});

const UserModel = model<User>('user', schema);

export default UserModel;
