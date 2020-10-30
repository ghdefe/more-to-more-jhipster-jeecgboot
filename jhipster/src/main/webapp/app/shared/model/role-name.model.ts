export interface IRoleName {
  id?: number;
  name?: string;
}

export class RoleName implements IRoleName {
  constructor(public id?: number, public name?: string) {}
}
