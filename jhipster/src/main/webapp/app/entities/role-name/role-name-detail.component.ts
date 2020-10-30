import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoleName } from 'app/shared/model/role-name.model';

@Component({
  selector: 'jhi-role-name-detail',
  templateUrl: './role-name-detail.component.html',
})
export class RoleNameDetailComponent implements OnInit {
  roleName: IRoleName | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleName }) => (this.roleName = roleName));
  }

  previousState(): void {
    window.history.back();
  }
}
