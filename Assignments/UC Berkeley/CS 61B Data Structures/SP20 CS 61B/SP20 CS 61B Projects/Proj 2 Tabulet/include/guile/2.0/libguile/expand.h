/* classes: h_files */

#ifndef SCM_EXPAND_H
#define SCM_EXPAND_H

/* Copyright (C) 2010, 2011
 * Free Software Foundation, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA
 */



#include "libguile/__scm.h"




#ifdef BUILDING_LIBGUILE

/* All private for now. Ask if you want to use this. Surely this should be
   auto-generated by something; for now I wrangle it with keyboard macros. */

typedef enum
  {
    SCM_EXPANDED_VOID,
    SCM_EXPANDED_CONST,
    SCM_EXPANDED_PRIMITIVE_REF,
    SCM_EXPANDED_LEXICAL_REF,
    SCM_EXPANDED_LEXICAL_SET,
    SCM_EXPANDED_MODULE_REF,
    SCM_EXPANDED_MODULE_SET,
    SCM_EXPANDED_TOPLEVEL_REF,
    SCM_EXPANDED_TOPLEVEL_SET,
    SCM_EXPANDED_TOPLEVEL_DEFINE,
    SCM_EXPANDED_CONDITIONAL,
    SCM_EXPANDED_APPLICATION,
    SCM_EXPANDED_SEQUENCE,
    SCM_EXPANDED_LAMBDA,
    SCM_EXPANDED_LAMBDA_CASE,
    SCM_EXPANDED_LET,
    SCM_EXPANDED_LETREC,
    SCM_EXPANDED_DYNLET,
    SCM_NUM_EXPANDED_TYPES,
  } scm_t_expanded_type;





/* {Expanded Source}
 */

SCM_INTERNAL SCM scm_exp_vtable_vtable;

enum
  {
    SCM_EXPANDED_TYPE_NAME = scm_vtable_offset_user,
    SCM_EXPANDED_TYPE_CODE,
    SCM_EXPANDED_TYPE_FIELDS,
  };

#define SCM_EXPANDED_P(x)                                               \
  (SCM_STRUCTP (x)                                                      \
   && (scm_is_eq (SCM_STRUCT_VTABLE (SCM_STRUCT_VTABLE (x)), scm_exp_vtable_vtable)))
#define SCM_EXPANDED_REF(x,type,field) \
  (scm_struct_ref (x, SCM_I_MAKINUM (SCM_EXPANDED_##type##_##field)))
#define SCM_EXPANDED_TYPE(x) \
  SCM_STRUCT_DATA_REF (SCM_STRUCT_VTABLE (x), SCM_EXPANDED_TYPE_CODE)





#define SCM_EXPANDED_VOID_TYPE_NAME "void"
#define SCM_EXPANDED_VOID_FIELD_NAMES \
  { "src" }  
enum
  {
    SCM_EXPANDED_VOID_SRC,
    SCM_NUM_EXPANDED_VOID_FIELDS,
  };
#define SCM_MAKE_EXPANDED_VOID(src) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_VOID], 0, SCM_NUM_EXPANDED_VOID_FIELDS, SCM_UNPACK (src))

#define SCM_EXPANDED_CONST_TYPE_NAME "const"
#define SCM_EXPANDED_CONST_FIELD_NAMES          \
  { "src", "exp", }
enum
  {
    SCM_EXPANDED_CONST_SRC,
    SCM_EXPANDED_CONST_EXP,
    SCM_NUM_EXPANDED_CONST_FIELDS,
  };
#define SCM_MAKE_EXPANDED_CONST(src, exp) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_CONST], 0, SCM_NUM_EXPANDED_CONST_FIELDS, SCM_UNPACK (src), SCM_UNPACK (exp))

#define SCM_EXPANDED_PRIMITIVE_REF_TYPE_NAME "primitive-ref"
#define SCM_EXPANDED_PRIMITIVE_REF_FIELD_NAMES  \
  { "src", "name", }
enum
  {
    SCM_EXPANDED_PRIMITIVE_REF_SRC,
    SCM_EXPANDED_PRIMITIVE_REF_NAME,
    SCM_NUM_EXPANDED_PRIMITIVE_REF_FIELDS,
  };
#define SCM_MAKE_EXPANDED_PRIMITIVE_REF(src, name) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_PRIMITIVE_REF], 0, SCM_NUM_EXPANDED_PRIMITIVE_REF_FIELDS, SCM_UNPACK (src), SCM_UNPACK (name))

#define SCM_EXPANDED_LEXICAL_REF_TYPE_NAME "lexical-ref"
#define SCM_EXPANDED_LEXICAL_REF_FIELD_NAMES    \
  { "src", "name", "gensym", }
enum
  {
    SCM_EXPANDED_LEXICAL_REF_SRC,
    SCM_EXPANDED_LEXICAL_REF_NAME,
    SCM_EXPANDED_LEXICAL_REF_GENSYM,
    SCM_NUM_EXPANDED_LEXICAL_REF_FIELDS,
  };
#define SCM_MAKE_EXPANDED_LEXICAL_REF(src, name, gensym) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_LEXICAL_REF], 0, SCM_NUM_EXPANDED_LEXICAL_REF_FIELDS, SCM_UNPACK (src), SCM_UNPACK (name), SCM_UNPACK (gensym))

#define SCM_EXPANDED_LEXICAL_SET_TYPE_NAME "lexical-set"
#define SCM_EXPANDED_LEXICAL_SET_FIELD_NAMES    \
  { "src", "name", "gensym", "exp", }
enum
  {
    SCM_EXPANDED_LEXICAL_SET_SRC,
    SCM_EXPANDED_LEXICAL_SET_NAME,
    SCM_EXPANDED_LEXICAL_SET_GENSYM,
    SCM_EXPANDED_LEXICAL_SET_EXP,
    SCM_NUM_EXPANDED_LEXICAL_SET_FIELDS,
  };
#define SCM_MAKE_EXPANDED_LEXICAL_SET(src, name, gensym, exp) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_LEXICAL_SET], 0, SCM_NUM_EXPANDED_LEXICAL_SET_FIELDS, SCM_UNPACK (src), SCM_UNPACK (name), SCM_UNPACK (gensym), SCM_UNPACK (exp))

#define SCM_EXPANDED_MODULE_REF_TYPE_NAME "module-ref"
#define SCM_EXPANDED_MODULE_REF_FIELD_NAMES     \
  { "src", "mod", "name", "public?", }
enum
  {
    SCM_EXPANDED_MODULE_REF_SRC,
    SCM_EXPANDED_MODULE_REF_MOD,
    SCM_EXPANDED_MODULE_REF_NAME,
    SCM_EXPANDED_MODULE_REF_PUBLIC,
    SCM_NUM_EXPANDED_MODULE_REF_FIELDS,
  };
#define SCM_MAKE_EXPANDED_MODULE_REF(src, mod, name, public) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_MODULE_REF], 0, SCM_NUM_EXPANDED_MODULE_REF_FIELDS, SCM_UNPACK (src), SCM_UNPACK (mod), SCM_UNPACK (name), SCM_UNPACK (public))

#define SCM_EXPANDED_MODULE_SET_TYPE_NAME "module-set"
#define SCM_EXPANDED_MODULE_SET_FIELD_NAMES     \
  { "src", "mod", "name", "public?", "exp", }
enum
  {
    SCM_EXPANDED_MODULE_SET_SRC,
    SCM_EXPANDED_MODULE_SET_MOD,
    SCM_EXPANDED_MODULE_SET_NAME,
    SCM_EXPANDED_MODULE_SET_PUBLIC,
    SCM_EXPANDED_MODULE_SET_EXP,
    SCM_NUM_EXPANDED_MODULE_SET_FIELDS,
  };
#define SCM_MAKE_EXPANDED_MODULE_SET(src, mod, name, public, exp) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_MODULE_SET], 0, SCM_NUM_EXPANDED_MODULE_SET_FIELDS, SCM_UNPACK (src), SCM_UNPACK (mod), SCM_UNPACK (name), SCM_UNPACK (public), SCM_UNPACK (exp))

#define SCM_EXPANDED_TOPLEVEL_REF_TYPE_NAME "toplevel-ref"
#define SCM_EXPANDED_TOPLEVEL_REF_FIELD_NAMES   \
  { "src", "name", }
enum
  {
    SCM_EXPANDED_TOPLEVEL_REF_SRC,
    SCM_EXPANDED_TOPLEVEL_REF_NAME,
    SCM_NUM_EXPANDED_TOPLEVEL_REF_FIELDS,
  };
#define SCM_MAKE_EXPANDED_TOPLEVEL_REF(src, name) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_TOPLEVEL_REF], 0, SCM_NUM_EXPANDED_TOPLEVEL_REF_FIELDS, SCM_UNPACK (src), SCM_UNPACK (name))

#define SCM_EXPANDED_TOPLEVEL_SET_TYPE_NAME "toplevel-set"
#define SCM_EXPANDED_TOPLEVEL_SET_FIELD_NAMES   \
  { "src", "name", "exp", }
enum
  {
    SCM_EXPANDED_TOPLEVEL_SET_SRC,
    SCM_EXPANDED_TOPLEVEL_SET_NAME,
    SCM_EXPANDED_TOPLEVEL_SET_EXP,
    SCM_NUM_EXPANDED_TOPLEVEL_SET_FIELDS,
  };
#define SCM_MAKE_EXPANDED_TOPLEVEL_SET(src, name, exp) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_TOPLEVEL_SET], 0, SCM_NUM_EXPANDED_TOPLEVEL_SET_FIELDS, SCM_UNPACK (src), SCM_UNPACK (name), SCM_UNPACK (exp))

#define SCM_EXPANDED_TOPLEVEL_DEFINE_TYPE_NAME "toplevel-define"
#define SCM_EXPANDED_TOPLEVEL_DEFINE_FIELD_NAMES        \
  { "src", "name", "exp", }
enum
  {
    SCM_EXPANDED_TOPLEVEL_DEFINE_SRC,
    SCM_EXPANDED_TOPLEVEL_DEFINE_NAME,
    SCM_EXPANDED_TOPLEVEL_DEFINE_EXP,
    SCM_NUM_EXPANDED_TOPLEVEL_DEFINE_FIELDS,
  };
#define SCM_MAKE_EXPANDED_TOPLEVEL_DEFINE(src, name, exp) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_TOPLEVEL_DEFINE], 0, SCM_NUM_EXPANDED_TOPLEVEL_DEFINE_FIELDS, SCM_UNPACK (src), SCM_UNPACK (name), SCM_UNPACK (exp))

#define SCM_EXPANDED_CONDITIONAL_TYPE_NAME "conditional"
#define SCM_EXPANDED_CONDITIONAL_FIELD_NAMES    \
  { "src", "test", "consequent", "alternate", }
enum
  {
    SCM_EXPANDED_CONDITIONAL_SRC,
    SCM_EXPANDED_CONDITIONAL_TEST,
    SCM_EXPANDED_CONDITIONAL_CONSEQUENT,
    SCM_EXPANDED_CONDITIONAL_ALTERNATE,
    SCM_NUM_EXPANDED_CONDITIONAL_FIELDS,
  };
#define SCM_MAKE_EXPANDED_CONDITIONAL(src, test, consequent, alternate) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_CONDITIONAL], 0, SCM_NUM_EXPANDED_CONDITIONAL_FIELDS, SCM_UNPACK (src), SCM_UNPACK (test), SCM_UNPACK (consequent), SCM_UNPACK (alternate))

#define SCM_EXPANDED_APPLICATION_TYPE_NAME "application"
#define SCM_EXPANDED_APPLICATION_FIELD_NAMES    \
  { "src", "proc", "args", }
enum
  {
    SCM_EXPANDED_APPLICATION_SRC,
    SCM_EXPANDED_APPLICATION_PROC,
    SCM_EXPANDED_APPLICATION_ARGS,
    SCM_NUM_EXPANDED_APPLICATION_FIELDS,
  };
#define SCM_MAKE_EXPANDED_APPLICATION(src, proc, args) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_APPLICATION], 0, SCM_NUM_EXPANDED_APPLICATION_FIELDS, SCM_UNPACK (src), SCM_UNPACK (proc), SCM_UNPACK (args))

#define SCM_EXPANDED_SEQUENCE_TYPE_NAME "sequence"
#define SCM_EXPANDED_SEQUENCE_FIELD_NAMES       \
  {  "src", "exps", }
enum
  {
    SCM_EXPANDED_SEQUENCE_SRC,
    SCM_EXPANDED_SEQUENCE_EXPS,
    SCM_NUM_EXPANDED_SEQUENCE_FIELDS,
  };
#define SCM_MAKE_EXPANDED_SEQUENCE(src, exps) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_SEQUENCE], 0, SCM_NUM_EXPANDED_SEQUENCE_FIELDS, SCM_UNPACK (src), SCM_UNPACK (exps))

#define SCM_EXPANDED_LAMBDA_TYPE_NAME "lambda"
#define SCM_EXPANDED_LAMBDA_FIELD_NAMES         \
  { "src", "meta", "body", }
enum
  {
    SCM_EXPANDED_LAMBDA_SRC,
    SCM_EXPANDED_LAMBDA_META,
    SCM_EXPANDED_LAMBDA_BODY,
    SCM_NUM_EXPANDED_LAMBDA_FIELDS,
  };
#define SCM_MAKE_EXPANDED_LAMBDA(src, meta, body) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_LAMBDA], 0, SCM_NUM_EXPANDED_LAMBDA_FIELDS, SCM_UNPACK (src), SCM_UNPACK (meta), SCM_UNPACK (body))

#define SCM_EXPANDED_LAMBDA_CASE_TYPE_NAME "lambda-case"
#define SCM_EXPANDED_LAMBDA_CASE_FIELD_NAMES    \
  { "src", "req", "opt", "rest", "kw", "inits", "gensyms", "body", "alternate", }
enum
  {
    SCM_EXPANDED_LAMBDA_CASE_SRC,
    SCM_EXPANDED_LAMBDA_CASE_REQ,
    SCM_EXPANDED_LAMBDA_CASE_OPT,
    SCM_EXPANDED_LAMBDA_CASE_REST,
    SCM_EXPANDED_LAMBDA_CASE_KW,
    SCM_EXPANDED_LAMBDA_CASE_INITS,
    SCM_EXPANDED_LAMBDA_CASE_GENSYMS,
    SCM_EXPANDED_LAMBDA_CASE_BODY,
    SCM_EXPANDED_LAMBDA_CASE_ALTERNATE,
    SCM_NUM_EXPANDED_LAMBDA_CASE_FIELDS,
  };
#define SCM_MAKE_EXPANDED_LAMBDA_CASE(src, req, opt, rest, kw, inits, gensyms, body, alternate) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_LAMBDA_CASE], 0, SCM_NUM_EXPANDED_LAMBDA_CASE_FIELDS, SCM_UNPACK (src), SCM_UNPACK (req), SCM_UNPACK (opt), SCM_UNPACK (rest), SCM_UNPACK (kw), SCM_UNPACK (inits), SCM_UNPACK (gensyms), SCM_UNPACK (body), SCM_UNPACK (alternate))

#define SCM_EXPANDED_LET_TYPE_NAME "let"
#define SCM_EXPANDED_LET_FIELD_NAMES            \
  { "src", "names", "gensyms", "vals", "body", }
enum
  {
    SCM_EXPANDED_LET_SRC,
    SCM_EXPANDED_LET_NAMES,
    SCM_EXPANDED_LET_GENSYMS,
    SCM_EXPANDED_LET_VALS,
    SCM_EXPANDED_LET_BODY,
    SCM_NUM_EXPANDED_LET_FIELDS,
  };
#define SCM_MAKE_EXPANDED_LET(src, names, gensyms, vals, body) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_LET], 0, SCM_NUM_EXPANDED_LET_FIELDS, SCM_UNPACK (src), SCM_UNPACK (names), SCM_UNPACK (gensyms), SCM_UNPACK (vals), SCM_UNPACK (body))

#define SCM_EXPANDED_LETREC_TYPE_NAME "letrec"
#define SCM_EXPANDED_LETREC_FIELD_NAMES         \
  { "src", "in-order?", "names", "gensyms", "vals", "body", }
enum
  {
    SCM_EXPANDED_LETREC_SRC,
    SCM_EXPANDED_LETREC_IN_ORDER_P,
    SCM_EXPANDED_LETREC_NAMES,
    SCM_EXPANDED_LETREC_GENSYMS,
    SCM_EXPANDED_LETREC_VALS,
    SCM_EXPANDED_LETREC_BODY,
    SCM_NUM_EXPANDED_LETREC_FIELDS,
  };
#define SCM_MAKE_EXPANDED_LETREC(src, in_order_p, names, gensyms, vals, body) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_LETREC], 0, SCM_NUM_EXPANDED_LETREC_FIELDS, SCM_UNPACK (src), SCM_UNPACK (in_order_p), SCM_UNPACK (names), SCM_UNPACK (gensyms), SCM_UNPACK (vals), SCM_UNPACK (body))

#define SCM_EXPANDED_DYNLET_TYPE_NAME "dynlet"
#define SCM_EXPANDED_DYNLET_FIELD_NAMES         \
  { "src", "fluids", "vals", "body", }
enum
  {
    SCM_EXPANDED_DYNLET_SRC,
    SCM_EXPANDED_DYNLET_FLUIDS,
    SCM_EXPANDED_DYNLET_VALS,
    SCM_EXPANDED_DYNLET_BODY,
    SCM_NUM_EXPANDED_DYNLET_FIELDS,
  };
#define SCM_MAKE_EXPANDED_DYNLET(src, fluids, vals, body) \
  scm_c_make_struct (exp_vtables[SCM_EXPANDED_DYNLET], 0, SCM_NUM_EXPANDED_DYNLET_FIELDS, SCM_UNPACK (src), SCM_UNPACK (fluids), SCM_UNPACK (vals), SCM_UNPACK (body))

#endif /* BUILDING_LIBGUILE */



SCM_INTERNAL SCM scm_macroexpand (SCM exp);
SCM_INTERNAL SCM scm_macroexpanded_p (SCM exp);

SCM_INTERNAL void scm_init_expand (void);


#endif  /* SCM_EXPAND_H */

/*
  Local Variables:
  c-file-style: "gnu"
  End:
*/