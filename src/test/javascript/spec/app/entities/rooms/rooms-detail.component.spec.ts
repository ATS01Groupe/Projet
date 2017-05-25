import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { HotManTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RoomsDetailComponent } from '../../../../../../main/webapp/app/entities/rooms/rooms-detail.component';
import { RoomsService } from '../../../../../../main/webapp/app/entities/rooms/rooms.service';
import { Rooms } from '../../../../../../main/webapp/app/entities/rooms/rooms.model';

describe('Component Tests', () => {

    describe('Rooms Management Detail Component', () => {
        let comp: RoomsDetailComponent;
        let fixture: ComponentFixture<RoomsDetailComponent>;
        let service: RoomsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HotManTestModule],
                declarations: [RoomsDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RoomsService,
                    EventManager
                ]
            }).overrideComponent(RoomsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RoomsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoomsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Rooms(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rooms).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
