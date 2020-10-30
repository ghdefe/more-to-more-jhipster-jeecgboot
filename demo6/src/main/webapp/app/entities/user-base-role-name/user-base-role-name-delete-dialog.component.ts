import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserBaseRoleName } from 'app/shared/model/user-base-role-name.model';
import { UserBaseRoleNameService } from './user-base-role-name.service';

@Component({
  templateUrl: './user-base-role-name-delete-dialog.component.html',
})
export class UserBaseRoleNameDeleteDialogComponent {
  userBaseRoleName?: IUserBaseRoleName;

  constructor(
    protected userBaseRoleNameService: UserBaseRoleNameService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userBaseRoleNameService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userBaseRoleNameListModification');
      this.activeModal.close();
    });
  }
}
