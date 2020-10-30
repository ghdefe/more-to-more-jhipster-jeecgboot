import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserBase } from 'app/shared/model/user-base.model';
import { UserBaseService } from './user-base.service';
import { UserBaseDeleteDialogComponent } from './user-base-delete-dialog.component';

@Component({
  selector: 'jhi-user-base',
  templateUrl: './user-base.component.html',
})
export class UserBaseComponent implements OnInit, OnDestroy {
  userBases?: IUserBase[];
  eventSubscriber?: Subscription;

  constructor(protected userBaseService: UserBaseService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.userBaseService.query().subscribe((res: HttpResponse<IUserBase[]>) => (this.userBases = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserBases();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserBase): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserBases(): void {
    this.eventSubscriber = this.eventManager.subscribe('userBaseListModification', () => this.loadAll());
  }

  delete(userBase: IUserBase): void {
    const modalRef = this.modalService.open(UserBaseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userBase = userBase;
  }
}
