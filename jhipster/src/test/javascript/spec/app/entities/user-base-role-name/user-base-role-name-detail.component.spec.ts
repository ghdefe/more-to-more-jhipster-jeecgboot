import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Demo6TestModule } from '../../../test.module';
import { UserBaseRoleNameDetailComponent } from 'app/entities/user-base-role-name/user-base-role-name-detail.component';
import { UserBaseRoleName } from 'app/shared/model/user-base-role-name.model';

describe('Component Tests', () => {
  describe('UserBaseRoleName Management Detail Component', () => {
    let comp: UserBaseRoleNameDetailComponent;
    let fixture: ComponentFixture<UserBaseRoleNameDetailComponent>;
    const route = ({ data: of({ userBaseRoleName: new UserBaseRoleName(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Demo6TestModule],
        declarations: [UserBaseRoleNameDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserBaseRoleNameDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserBaseRoleNameDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userBaseRoleName on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userBaseRoleName).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
