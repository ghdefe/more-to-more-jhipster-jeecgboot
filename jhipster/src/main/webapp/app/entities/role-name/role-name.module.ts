import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Demo6SharedModule } from 'app/shared/shared.module';
import { RoleNameComponent } from './role-name.component';
import { RoleNameDetailComponent } from './role-name-detail.component';
import { RoleNameUpdateComponent } from './role-name-update.component';
import { RoleNameDeleteDialogComponent } from './role-name-delete-dialog.component';
import { roleNameRoute } from './role-name.route';

@NgModule({
  imports: [Demo6SharedModule, RouterModule.forChild(roleNameRoute)],
  declarations: [RoleNameComponent, RoleNameDetailComponent, RoleNameUpdateComponent, RoleNameDeleteDialogComponent],
  entryComponents: [RoleNameDeleteDialogComponent],
})
export class Demo6RoleNameModule {}
