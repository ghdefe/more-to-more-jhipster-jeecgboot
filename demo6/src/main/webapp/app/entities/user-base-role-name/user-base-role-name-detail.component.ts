import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserBaseRoleName } from 'app/shared/model/user-base-role-name.model';

@Component({
  selector: 'jhi-user-base-role-name-detail',
  templateUrl: './user-base-role-name-detail.component.html',
})
export class UserBaseRoleNameDetailComponent implements OnInit {
  userBaseRoleName: IUserBaseRoleName | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userBaseRoleName }) => (this.userBaseRoleName = userBaseRoleName));
  }

  previousState(): void {
    window.history.back();
  }
}
