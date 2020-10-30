import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRoleName, RoleName } from 'app/shared/model/role-name.model';
import { RoleNameService } from './role-name.service';

@Component({
  selector: 'jhi-role-name-update',
  templateUrl: './role-name-update.component.html',
})
export class RoleNameUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected roleNameService: RoleNameService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleName }) => {
      this.updateForm(roleName);
    });
  }

  updateForm(roleName: IRoleName): void {
    this.editForm.patchValue({
      id: roleName.id,
      name: roleName.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const roleName = this.createFromForm();
    if (roleName.id !== undefined) {
      this.subscribeToSaveResponse(this.roleNameService.update(roleName));
    } else {
      this.subscribeToSaveResponse(this.roleNameService.create(roleName));
    }
  }

  private createFromForm(): IRoleName {
    return {
      ...new RoleName(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleName>>): void {
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
