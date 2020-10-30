import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Demo6TestModule } from '../../../test.module';
import { UserBaseComponent } from 'app/entities/user-base/user-base.component';
import { UserBaseService } from 'app/entities/user-base/user-base.service';
import { UserBase } from 'app/shared/model/user-base.model';

describe('Component Tests', () => {
  describe('UserBase Management Component', () => {
    let comp: UserBaseComponent;
    let fixture: ComponentFixture<UserBaseComponent>;
    let service: UserBaseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Demo6TestModule],
        declarations: [UserBaseComponent],
      })
        .overrideTemplate(UserBaseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserBaseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserBaseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserBase(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userBases && comp.userBases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
