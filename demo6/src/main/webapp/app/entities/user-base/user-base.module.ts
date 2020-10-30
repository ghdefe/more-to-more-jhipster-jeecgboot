import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Demo6SharedModule } from 'app/shared/shared.module';
import { UserBaseComponent } from './user-base.component';
import { UserBaseDetailComponent } from './user-base-detail.component';
import { UserBaseUpdateComponent } from './user-base-update.component';
import { UserBaseDeleteDialogComponent } from './user-base-delete-dialog.component';
import { userBaseRoute } from './user-base.route';

@NgModule({
  imports: [Demo6SharedModule, RouterModule.forChild(userBaseRoute)],
  declarations: [UserBaseComponent, UserBaseDetailComponent, UserBaseUpdateComponent, UserBaseDeleteDialogComponent],
  entryComponents: [UserBaseDeleteDialogComponent],
})
export class Demo6UserBaseModule {}
