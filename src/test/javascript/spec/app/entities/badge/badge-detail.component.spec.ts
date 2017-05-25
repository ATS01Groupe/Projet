import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { HotManTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BadgeDetailComponent } from '../../../../../../main/webapp/app/entities/badge/badge-detail.component';
import { BadgeService } from '../../../../../../main/webapp/app/entities/badge/badge.service';
import { Badge } from '../../../../../../main/webapp/app/entities/badge/badge.model';

describe('Component Tests', () => {

    describe('Badge Management Detail Component', () => {
        let comp: BadgeDetailComponent;
        let fixture: ComponentFixture<BadgeDetailComponent>;
        let service: BadgeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HotManTestModule],
                declarations: [BadgeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BadgeService,
                    EventManager
                ]
            }).overrideComponent(BadgeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BadgeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BadgeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Badge(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.badge).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
