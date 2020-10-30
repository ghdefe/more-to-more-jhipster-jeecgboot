import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Demo6TestModule } from '../../../test.module';
import { RoleNameComponent } from 'app/entities/role-name/role-name.component';
import { RoleNameService } from 'app/entities/role-name/role-name.service';
import { RoleName } from 'app/shared/model/role-name.model';

describe('Component Tests', () => {
  describe('RoleName Management Component', () => {
    let comp: RoleNameComponent;
    let fixture: ComponentFixture<RoleNameComponent>;
    let service: RoleNameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Demo6TestModule],
        declarations: [RoleNameComponent],
      })
        .overrideTemplate(RoleNameComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RoleNameComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RoleNameService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RoleName(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.roleNames && comp.roleNames[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
