import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Demo6TestModule } from '../../../test.module';
import { UserBaseRoleNameUpdateComponent } from 'app/entities/user-base-role-name/user-base-role-name-update.component';
import { UserBaseRoleNameService } from 'app/entities/user-base-role-name/user-base-role-name.service';
import { UserBaseRoleName } from 'app/shared/model/user-base-role-name.model';

describe('Component Tests', () => {
  describe('UserBaseRoleName Management Update Component', () => {
    let comp: UserBaseRoleNameUpdateComponent;
    let fixture: ComponentFixture<UserBaseRoleNameUpdateComponent>;
    let service: UserBaseRoleNameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Demo6TestModule],
        declarations: [UserBaseRoleNameUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserBaseRoleNameUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserBaseRoleNameUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserBaseRoleNameService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserBaseRoleName(123);
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
        const entity = new UserBaseRoleName();
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
