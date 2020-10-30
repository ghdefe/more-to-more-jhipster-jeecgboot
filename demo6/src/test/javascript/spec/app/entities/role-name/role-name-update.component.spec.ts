import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Demo6TestModule } from '../../../test.module';
import { RoleNameUpdateComponent } from 'app/entities/role-name/role-name-update.component';
import { RoleNameService } from 'app/entities/role-name/role-name.service';
import { RoleName } from 'app/shared/model/role-name.model';

describe('Component Tests', () => {
  describe('RoleName Management Update Component', () => {
    let comp: RoleNameUpdateComponent;
    let fixture: ComponentFixture<RoleNameUpdateComponent>;
    let service: RoleNameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Demo6TestModule],
        declarations: [RoleNameUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RoleNameUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RoleNameUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RoleNameService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RoleName(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RoleName();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
