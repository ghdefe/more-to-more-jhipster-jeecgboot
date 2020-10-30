import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Demo6TestModule } from '../../../test.module';
import { RoleNameDetailComponent } from 'app/entities/role-name/role-name-detail.component';
import { RoleName } from 'app/shared/model/role-name.model';

describe('Component Tests', () => {
  describe('RoleName Management Detail Component', () => {
    let comp: RoleNameDetailComponent;
    let fixture: ComponentFixture<RoleNameDetailComponent>;
    const route = ({ data: of({ roleName: new RoleName(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Demo6TestModule],
        declarations: [RoleNameDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RoleNameDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RoleNameDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load roleName on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.roleName).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
