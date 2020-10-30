import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserBase } from 'app/shared/model/user-base.model';
import { UserBaseService } from './user-base.service';

@Component({
  templateUrl: './user-base-delete-dialog.component.html',
})
export class UserBaseDeleteDialogComponent {
  userBase?: IUserBase;

  constructor(protected userBaseService: UserBaseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userBaseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userBaseListModification');
      this.activeModal.close();
    });
  }
}
