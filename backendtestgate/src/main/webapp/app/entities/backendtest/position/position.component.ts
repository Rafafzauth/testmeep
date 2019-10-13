import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPosition } from 'app/shared/model/backendtest/position.model';
import { AccountService } from 'app/core/auth/account.service';
import { PositionService } from './position.service';

@Component({
  selector: 'jhi-position',
  templateUrl: './position.component.html'
})
export class PositionComponent implements OnInit, OnDestroy {
  positions: IPosition[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected positionService: PositionService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.positionService
      .query()
      .pipe(
        filter((res: HttpResponse<IPosition[]>) => res.ok),
        map((res: HttpResponse<IPosition[]>) => res.body)
      )
      .subscribe((res: IPosition[]) => {
        this.positions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPositions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPosition) {
    return item.id;
  }

  registerChangeInPositions() {
    this.eventSubscriber = this.eventManager.subscribe('positionListModification', response => this.loadAll());
  }
}
