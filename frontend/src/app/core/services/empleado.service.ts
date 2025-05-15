import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {
  private baseUrl = 'http://localhost:8080/api/empleados';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any>(this.baseUrl).pipe(
      map(response =>
        (response.data || []).map((item: any) => {
          const attr = item.attributes;
          return {
            id: item.id,
            employee_name: attr.employee_name,
            employee_salary: attr.employee_salary,
            salary_anual: attr.employee_salary * 12,
            employee_age: attr.employee_age,
            profile_image: attr.profile_image
          };
        })
      )
    );
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`).pipe(
      map(response => {
        const item = response.data;
        const attr = item.attributes;
        return {
          id: item.id,
          employee_name: attr.employee_name,
          employee_salary: attr.employee_salary,
          salary_anual: attr.employee_salary * 12,
          employee_age: attr.employee_age,
          profile_image: attr.profile_image
        };
      })
    );
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}