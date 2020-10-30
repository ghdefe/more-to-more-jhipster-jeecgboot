import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRoleName, RoleName } from 'app/shared/model/role-name.model';
import { RoleNameService } from './role-name.service';
import { RoleNameComponent } from './role-name.component';
import { RoleNameDetailComponent } from './role-name-detail.component';
import { RoleNameUpdateComponent } from './role-name-update.component';

@Injectable({ providedIn: 'root' })
export class RoleNameResolve implements Resolve<IRoleName> {
  constructor(private service: RoleNameService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRoleName> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((roleName: HttpResponse<RoleName>) => {
          if (roleName.body) {
            return of(roleName.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RoleName());
  }
}

export const roleNameRoute: Routes = [
  {
    path: '',
    component: RoleNameComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.roleName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RoleNameDetailComponent,
    resolve: {
      roleName: RoleNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.roleName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RoleNameUpdateComponent,
    resolve: {
      roleName: RoleNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.roleName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RoleNameUpdateComponent,
    resolve: {
      roleName: RoleNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.roleName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
