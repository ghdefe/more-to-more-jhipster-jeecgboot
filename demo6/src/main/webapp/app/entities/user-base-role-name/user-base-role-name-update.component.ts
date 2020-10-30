import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserBaseRoleName, UserBaseRoleName } from 'app/shared/model/user-base-role-name.model';
import { UserBaseRoleNameService } from './user-base-role-name.service';

@Component({
  selector: 'jhi-user-base-role-name-update',
  templateUrl: './user-base-role-name-update.component.html',
})
export class UserBaseRoleNameUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userBaseId: [],
    roleNameId: [],
  });

  constructor(
    protected userBaseRoleNameService: UserBaseRoleNameService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userBaseRoleName }) => {
      this.updateForm(userBaseRoleName);
    });
  }

  updateForm(userBaseRoleName: IUserBaseRoleName): void {
    this.editForm.patchValue({
      id: userBaseRoleName.id,
      userBaseId: userBaseRoleName.userBaseId,
      roleNameId: userBaseRoleName.roleNameId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userBaseRoleName = this.createFromForm();
    if (userBaseRoleName.id !== undefined) {
      this.subscribeToSaveResponse(this.userBaseRoleNameService.update(userBaseRoleName));
    } else {
      this.subscribeToSaveResponse(this.userBaseRoleNameService.create(userBaseRoleName));
    }
  }

  private createFromForm(): IUserBaseRoleName {
    return {
      ...new UserBaseRoleName(),
      id: this.editForm.get(['id'])!.value,
      userBaseId: this.editForm.get(['userBaseId'])!.value,
      roleNameId: this.editForm.get(['roleNameId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserBaseRoleName>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
