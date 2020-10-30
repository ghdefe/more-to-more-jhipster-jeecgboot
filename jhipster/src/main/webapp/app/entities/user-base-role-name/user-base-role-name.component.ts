import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserBaseRoleName } from 'app/shared/model/user-base-role-name.model';
import { UserBaseRoleNameService } from './user-base-role-name.service';
import { UserBaseRoleNameDeleteDialogComponent } from './user-base-role-name-delete-dialog.component';

@Component({
  selector: 'jhi-user-base-role-name',
  templateUrl: './user-base-role-name.component.html',
})
export class UserBaseRoleNameComponent implements OnInit, OnDestroy {
  userBaseRoleNames?: IUserBaseRoleName[];
  eventSubscriber?: Subscription;

  constructor(
    protected userBaseRoleNameService: UserBaseRoleNameService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.userBaseRoleNameService.query().subscribe((res: HttpResponse<IUserBaseRoleName[]>) => (this.userBaseRoleNames = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserBaseRoleNames();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserBaseRoleName): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserBaseRoleNames(): void {
    this.eventSubscriber = this.eventManager.subscribe('userBaseRoleNameListModification', () => this.loadAll());
  }

  delete(userBaseRoleName: IUserBaseRoleName): void {
    const modalRef = this.modalService.open(UserBaseRoleNameDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userBaseRoleName = userBaseRoleName;
  }
}
