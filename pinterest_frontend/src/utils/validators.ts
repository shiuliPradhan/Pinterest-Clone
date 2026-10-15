// src/utils/validators.ts

export const validators = {
  required: (val: string) => val.trim().length > 0,

  email: (val: string) =>
    /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(val.trim()),

  password: (val: string) =>
    /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/.test(val),

  url: (val: string) =>
    /^(https?:\/\/)[\w.-]+(\.[\w\.-]+)+[\w\-\._~:\/?#[\]@!\$&'\(\)\*\+,;=.]+$/.test(
      val.trim()
    ),

  minLength: (val: string, min: number) => val.trim().length >= min,
  maxLength: (val: string, max: number) => val.trim().length <= max
};

export default validators;
