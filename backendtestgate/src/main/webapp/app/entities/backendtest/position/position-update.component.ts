import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPosition, Position } from 'app/shared/model/backendtest/position.model';
import { PositionService } from './position.service';

@Component({
  selector: 'jhi-position-update',
  templateUrl: './position-update.component.html'
})
export class PositionUpdateComponent implements OnInit {
  isSaving: boolean;
  updateDp: any;

  editForm = this.fb.group({
    id: [],
    lowerLeftLatLon: [],
    upperRightLatLon: [],
    companyZoneIds: [],
    result: [],
    update: []
  });

  constructor(protected positionService: PositionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ position }) => {
      this.updateForm(position);
    });
  }

  updateForm(position: IPosition) {
    this.editForm.patchValue({
      id: position.id,
      lowerLeftLatLon: position.lowerLeftLatLon,
      upperRightLatLon: position.upperRightLatLon,
      companyZoneIds: position.companyZoneIds,
      result: position.result,
      update: position.update
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const position = this.createFromForm();
    if (position.id !== undefined) {
      this.subscribeToSaveResponse(this.positionService.update(position));
    } else {
      this.subscribeToSaveResponse(this.positionService.create(position));
    }
  }

  private createFromForm(): IPosition {
    return {
      ...new Position(),
      id: this.editForm.get(['id']).value,
      lowerLeftLatLon: this.editForm.get(['lowerLeftLatLon']).value,
      upperRightLatLon: this.editForm.get(['upperRightLatLon']).value,
      companyZoneIds: this.editForm.get(['companyZoneIds']).value,
      result: this.editForm.get(['result']).value,
      update: this.editForm.get(['update']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPosition>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
