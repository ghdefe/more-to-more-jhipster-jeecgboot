import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserBase, UserBase } from 'app/shared/model/user-base.model';
import { UserBaseService } from './user-base.service';

@Component({
  selector: 'jhi-user-base-update',
  templateUrl: './user-base-update.component.html',
})
export class UserBaseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    username: [],
    phone: [null, [Validators.pattern('^[a-zA-Z0-9]*$')]],
  });

  constructor(protected userBaseService: UserBaseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userBase }) => {
      this.updateForm(userBase);
    });
  }

  updateForm(userBase: IUserBase): void {
    this.editForm.patchValue({
      id: userBase.id,
      username: userBase.username,
      phone: userBase.phone,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userBase = this.createFromForm();
    if (userBase.id !== undefined) {
      this.subscribeToSaveResponse(this.userBaseService.update(userBase));
    } else {
      this.subscribeToSaveResponse(this.userBaseService.create(userBase));
    }
  }

  private createFromForm(): IUserBase {
    return {
      ...new UserBase(),
      id: this.editForm.get(['id'])!.value,
      username: this.editForm.get(['username'])!.value,
      phone: this.editForm.get(['phone'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserBase>>): void {
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
