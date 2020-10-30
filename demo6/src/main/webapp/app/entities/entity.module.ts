import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-base',
        loadChildren: () => import('./user-base/user-base.module').then(m => m.Demo6UserBaseModule),
      },
      {
        path: 'role-name',
        loadChildren: () => import('./role-name/role-name.module').then(m => m.Demo6RoleNameModule),
      },
      {
        path: 'user-base-role-name',
        loadChildren: () => import('./user-base-role-name/user-base-role-name.module').then(m => m.Demo6UserBaseRoleNameModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class Demo6EntityModule {}
