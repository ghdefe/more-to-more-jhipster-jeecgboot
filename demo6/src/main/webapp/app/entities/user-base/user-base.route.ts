import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserBase, UserBase } from 'app/shared/model/user-base.model';
import { UserBaseService } from './user-base.service';
import { UserBaseComponent } from './user-base.component';
import { UserBaseDetailComponent } from './user-base-detail.component';
import { UserBaseUpdateComponent } from './user-base-update.component';

@Injectable({ providedIn: 'root' })
export class UserBaseResolve implements Resolve<IUserBase> {
  constructor(private service: UserBaseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserBase> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userBase: HttpResponse<UserBase>) => {
          if (userBase.body) {
            return of(userBase.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserBase());
  }
}

export const userBaseRoute: Routes = [
  {
    path: '',
    component: UserBaseComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.userBase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserBaseDetailComponent,
    resolve: {
      userBase: UserBaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.userBase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserBaseUpdateComponent,
    resolve: {
      userBase: UserBaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.userBase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserBaseUpdateComponent,
    resolve: {
      userBase: UserBaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demo6App.userBase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
