export interface Commercant {
  id: number;
  nom: string;
}

export interface RendezVous {
  id: number
  clientId: number
  commercantId: number
  appointmentDate: string
  serviceType: string
  status: string
  fidelityPoints: number
}
