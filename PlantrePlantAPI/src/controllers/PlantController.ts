/** 
 DATE:		23-February-2022 
 * @name plantController.ts 
 * @author Shaq Purcell 
 * @version 
 * @description Contains all of the functions that gather data from the plant DB
 */

import { Request, Response } from 'express';
import PlantModel from '../models/plant';

class PlantController {
  listPlants = (req: Request, res: Response) => {
    const plants = PlantModel.find({ name: { $ne: null }, scientific_name: { $ne: null } }, (err, docs) => {
      if (err) {
        res.json({ data: [] });
      } else {
        console.log('Hit here');
        res.json({ data: docs.slice(0, 100) });
      }
    }).lean();
  };
}

export default new PlantController();
