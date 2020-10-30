export interface IUserBaseRoleName {
  id?: number;
  userBaseId?: number;
  roleNameId?: number;
}

export class UserBaseRoleName implements IUserBaseRoleName {
  constructor(public id?: number, public userBaseId?: number, public roleNameId?: number) {}
}
