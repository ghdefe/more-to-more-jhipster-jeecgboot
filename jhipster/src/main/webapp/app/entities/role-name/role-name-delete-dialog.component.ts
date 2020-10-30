import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoleName } from 'app/shared/model/role-name.model';
import { RoleNameService } from './role-name.service';

@Component({
  templateUrl: './role-name-delete-dialog.component.html',
})
export class RoleNameDeleteDialogComponent {
  roleName?: IRoleName;

  constructor(protected roleNameService: RoleNameService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.roleNameService.delete(id).subscribe(() => {
      this.eventManager.broadcast('roleNameListModification');
      this.activeModal.close();
    });
  }
}
