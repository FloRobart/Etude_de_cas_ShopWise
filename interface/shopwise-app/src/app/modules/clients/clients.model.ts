export interface Client {
  id?: number;
  nom: string;
  prenom?: string | null;
  email: string;
  phone?: string | null;
  hashPassword?: string | null;
}
