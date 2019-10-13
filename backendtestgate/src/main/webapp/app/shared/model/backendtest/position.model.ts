import { Moment } from 'moment';

export interface IPosition {
  id?: number;
  lowerLeftLatLon?: string;
  upperRightLatLon?: string;
  companyZoneIds?: string;
  result?: string;
  update?: Moment;
}

export class Position implements IPosition {
  constructor(
    public id?: number,
    public lowerLeftLatLon?: string,
    public upperRightLatLon?: string,
    public companyZoneIds?: string,
    public result?: string,
    public update?: Moment
  ) {}
}
