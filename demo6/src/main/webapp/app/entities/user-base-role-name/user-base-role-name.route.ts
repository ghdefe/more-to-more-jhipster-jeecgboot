import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserBaseRoleName, UserBaseRoleName } from 'app/shared/model/user-base-role-name.model';
import { UserBaseRoleNameService } from './user-base-role-name.service';
import { UserBaseRoleNameComponent } from './user-base-role-name.component';
import { UserBaseRoleNameDetailComponent } from './user-base-role-name-detail.component';
import { UserBaseRoleNameUpdateComponent } from './user-base-role-name-update.component';

@Injectable({ providedIn: 'root' })
export class UserBaseRoleNameResolve implements Resolve<IUserBaseRoleName> {
  constructor(private service: UserBaseRoleNameService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserBaseRoleName> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userBaseRoleName: HttpResponse<UserBaseRoleName>) => {
          if (userBaseRoleName.body) {
            return of(userBaseRoleName.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserBaseRoleName());
  }
}

export const userBaseRoleNameRoute: Routes = [
  {
    path: '',
    component: UserBaseRoleNameComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.userBaseRoleName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserBaseRoleNameDetailComponent,
    resolve: {
      userBaseRoleName: UserBaseRoleNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.userBaseRoleName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserBaseRoleNameUpdateComponent,
    resolve: {
      userBaseRoleName: UserBaseRoleNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.userBaseRoleName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserBaseRoleNameUpdateComponent,
    resolve: {
      userBaseRoleName: UserBaseRoleNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.userBaseRoleName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
