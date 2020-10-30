export interface IUserBase {
  id?: number;
  username?: string;
  phone?: string;
}

export class UserBase implements IUserBase {
  constructor(public id?: number, public username?: string, public phone?: string) {}
}
