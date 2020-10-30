import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Demo6TestModule } from '../../../test.module';
import { UserBaseRoleNameComponent } from 'app/entities/user-base-role-name/user-base-role-name.component';
import { UserBaseRoleNameService } from 'app/entities/user-base-role-name/user-base-role-name.service';
import { UserBaseRoleName } from 'app/shared/model/user-base-role-name.model';

describe('Component Tests', () => {
  describe('UserBaseRoleName Management Component', () => {
    let comp: UserBaseRoleNameComponent;
    let fixture: ComponentFixture<UserBaseRoleNameComponent>;
    let service: UserBaseRoleNameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Demo6TestModule],
        declarations: [UserBaseRoleNameComponent],
      })
        .overrideTemplate(UserBaseRoleNameComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserBaseRoleNameComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserBaseRoleNameService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserBaseRoleName(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userBaseRoleNames && comp.userBaseRoleNames[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
