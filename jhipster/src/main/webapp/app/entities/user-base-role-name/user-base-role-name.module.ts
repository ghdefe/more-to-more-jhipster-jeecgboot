import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Demo6SharedModule } from 'app/shared/shared.module';
import { UserBaseRoleNameComponent } from './user-base-role-name.component';
import { UserBaseRoleNameDetailComponent } from './user-base-role-name-detail.component';
import { UserBaseRoleNameUpdateComponent } from './user-base-role-name-update.component';
import { UserBaseRoleNameDeleteDialogComponent } from './user-base-role-name-delete-dialog.component';
import { userBaseRoleNameRoute } from './user-base-role-name.route';

@NgModule({
  imports: [Demo6SharedModule, RouterModule.forChild(userBaseRoleNameRoute)],
  declarations: [
    UserBaseRoleNameComponent,
    UserBaseRoleNameDetailComponent,
    UserBaseRoleNameUpdateComponent,
    UserBaseRoleNameDeleteDialogComponent,
  ],
  entryComponents: [UserBaseRoleNameDeleteDialogComponent],
})
export class Demo6UserBaseRoleNameModule {}
