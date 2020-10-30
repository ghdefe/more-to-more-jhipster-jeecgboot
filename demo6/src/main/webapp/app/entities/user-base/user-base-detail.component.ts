import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserBase } from 'app/shared/model/user-base.model';

@Component({
  selector: 'jhi-user-base-detail',
  templateUrl: './user-base-detail.component.html',
})
export class UserBaseDetailComponent implements OnInit {
  userBase: IUserBase | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userBase }) => (this.userBase = userBase));
  }

  previousState(): void {
    window.history.back();
  }
}
