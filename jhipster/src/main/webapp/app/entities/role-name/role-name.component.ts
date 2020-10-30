import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoleName } from 'app/shared/model/role-name.model';
import { RoleNameService } from './role-name.service';
import { RoleNameDeleteDialogComponent } from './role-name-delete-dialog.component';

@Component({
  selector: 'jhi-role-name',
  templateUrl: './role-name.component.html',
})
export class RoleNameComponent implements OnInit, OnDestroy {
  roleNames?: IRoleName[];
  eventSubscriber?: Subscription;

  constructor(protected roleNameService: RoleNameService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.roleNameService.query().subscribe((res: HttpResponse<IRoleName[]>) => (this.roleNames = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRoleNames();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRoleName): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRoleNames(): void {
    this.eventSubscriber = this.eventManager.subscribe('roleNameListModification', () => this.loadAll());
  }

  delete(roleName: IRoleName): void {
    const modalRef = this.modalService.open(RoleNameDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.roleName = roleName;
  }
}
