import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { HotManTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GroupeDetailComponent } from '../../../../../../main/webapp/app/entities/groupe/groupe-detail.component';
import { GroupeService } from '../../../../../../main/webapp/app/entities/groupe/groupe.service';
import { Groupe } from '../../../../../../main/webapp/app/entities/groupe/groupe.model';

describe('Component Tests', () => {

    describe('Groupe Management Detail Component', () => {
        let comp: GroupeDetailComponent;
        let fixture: ComponentFixture<GroupeDetailComponent>;
        let service: GroupeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HotManTestModule],
                declarations: [GroupeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GroupeService,
                    EventManager
                ]
            }).overrideComponent(GroupeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GroupeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Groupe(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.groupe).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
