import sys , os ;
oo000 = 0
try :
 from Tkinter import * ;
 import tkMessageBox ;
 oo000 = 1
except :
 oo000 = 0
 if 9 - 9: Ii . o0o00Oo0O - iI11I1II1I1I
 if 71 - 71: ii
iIIii1IIi = {
 "OK" : "OK" ,
 "CANCEL" : "Annulla" ,
 "PIN" : "PIN:" ,
 "PUK" : "PUK:" ,
 "OLD_PIN" : "Vecchio PIN:" ,
 "NEW_PIN" : "Nuovo PIN:" ,
 "RETYPE_NEW_PIN" : "Ripetere PIN:" ,
 "OLD_PUK" : "Vecchio PUK:" ,
 "NEW_PUK" : "Nuovo PUK:" ,
 "RETYPE_NEW_PUK" : "Ripetere PUK:" ,
 "TITLE_GET_PIN" : "Digitare il PIN di firma forte" ,
 "TITLE_UNBLOCK_PIN" : "Sblocco del PIN di firma forte" ,
 "TITLE_CHANGE_PUK" : "Cambio del PUK di firma forte" ,
 "TITLE_CHANGE_PIN" : "Cambio del PIN di firma forte" ,
 "MSG_NO_BLANK_PIN" : "Il PIN non puo' essere vuoto" ,
 "MSG_NO_BLANK_PUK" : "Il PUK non puo' essere vuoto" ,
 "MSG_NO_BLANK_OLD_PIN" : "Il vecchio PIN non puo' essere vuoto" ,
 "MSG_NO_BLANK_OLD_PUK" : "Il vecchio PUK non puo' essere vuoto" ,
 "MSG_NO_BLANK_NEW_PIN" : "Il nuovo PIN non puo' essere vuoto" ,
 "MSG_NEW_PIN_DIFFERENT" : "Il nuovo PIN e' stato ridigitato in maniera diversa" ,
 "MSG_NO_BLANK_NEW_PUK" : "Il nuovo PUK non puo' essere vuoto" ,
 "MSG_NO_BLANK_NEW_PUK" : "Il nuovo PUK non puo' essere vuoto" ,
 "MSG_NEW_PUK_DIFFERENT" : "Il nuovo PUK e' stato ridigitato in maniera diversa" ,
 "TITLE_WARNING" : "Attenzione" ,
 "TITLE_INFORMATION" : "Middleware Universale" ,
 "MESSAGE_BOX_OPERATION_OK" : "Operazione conclusa con successo" ,
 "MESSAGE_BOX_OPERATION_FAILED" : "Si e' verificato un errore" ,
 "MESSAGE_BOX_PIN_INCORRECT" : "Il PIN specificato non e' corretto. Si vuole riprovare?" ,
 "MESSAGE_BOX_PIN_LOCKED" : "La Smart Card e' bloccata" ,
 "MESSAGE_BOX_PIN_DEACTIVATED" : "Il PIN e' disattivato" ,
 "MESSAGE_BOX_PIN_LOCKED2" : "Il PIN e' bloccato" ,
 "MESSAGE_BOX_PUK_INCORRECT" : "Il PUK specificato non e' corretto. Si vuole riprovare?" ,
 "MESSAGE_BOX_PUK_LOCKED" : "Il PUK e' bloccato" ,
 "MESSAGE_BOX_CONFIRM_SIGNATURE" : "Confermare l'operazione di firma?" ,

 }
if 73 - 73: II111iiii
class IiII1IiiIiI1 :
 def __init__ ( self , master , lang_table = iIIii1IIi ) :
  self . root = master
  iIiiiI1IiI1I1 = Frame ( master )
  iIiiiI1IiI1I1 . pack ( )
  self . frame = iIiiiI1IiI1I1
  self . result = ( 0 , )
  if 87 - 87: OoOoOO00
  master . protocol ( "WM_DELETE_WINDOW" , master . destroy )
  if 27 - 27: OOOo0 / Oo - Ooo00oOo00o . I1IiI
  self . initial_focus = self . body ( iIiiiI1IiI1I1 , master )
  iIiiiI1IiI1I1 . pack ( padx = 5 , pady = 5 )
  self . buttonbox ( iIiiiI1IiI1I1 , master )
  if 73 - 73: OOooOOo / ii11ii1ii
  if not self . initial_focus :
   self . initial_focus = iIiiiI1IiI1I1
   if 94 - 94: OoOO + OoOO0ooOOoo0O + o0000oOoOoO0o * o00O0oo
  self . initial_focus . focus_set ( )
  if 97 - 97: oO0o0ooO0 - IIII / O0oO - o0oO0
  oo00 = master
  oo00 . update_idletasks ( )
  o00 = oo00 . winfo_width ( )
  Oo0oO0ooo = oo00 . winfo_height ( )
  o0oOoO00o = oo00 . winfo_screenwidth ( ) - o00
  i1 = oo00 . winfo_screenheight ( ) - Oo0oO0ooo
  oo00 . geometry ( "%dx%d%+d%+d" % ( o00 , Oo0oO0ooo , o0oOoO00o / 2 , i1 / 4 ) )
  if 64 - 64: ii11ii1ii % Ooo00oOo00o
 def body ( self , master , root ) :
 # create dialog body.  return widget that should have
 # initial focus.  this method should be overridden
  if 1 - 1: IIII
  pass
  if 91 - 91: ii11ii1ii * iI11I1II1I1I . IIII / o00O0oo
 def buttonbox ( self , master , root ) :
  if 87 - 87: II111iiii / o00O0oo . Ooo00oOo00o * ii - IIII * o0oO0
  if 82 - 82: o0000oOoOoO0o . O0oO / IIII % OoOoOO00 % iI11I1II1I1I % IIII
  if 86 - 86: I1IiI % OOOo0
  oo = Frame ( master )
  if 33 - 33: OoOoOO00 * Oo - OOooOOo * iI11I1II1I1I * ii * o0oO0
  i1iIIII = Button ( oo , text = "OK" , width = 10 , command = self . ok , default = ACTIVE )
  i1iIIII . bind ( "<Return>" , self . ok )
  i1iIIII . pack ( side = LEFT , padx = 5 , pady = 5 )
  I1 = Button ( oo , text = "Annulla" , width = 10 , command = self . cancel )
  I1 . bind ( "<Return>" , self . cancel )
  I1 . pack ( side = LEFT , padx = 5 , pady = 5 )
  if 54 - 54: OoOO0ooOOoo0O % o0o00Oo0O + OOOo0 - oO0o0ooO0 / o0000oOoOoO0o
  if 31 - 31: Ooo00oOo00o + OoOoOO00
  root . bind ( "<Escape>" , self . cancel )
  root . focus_set ( )
  oo . pack ( )
  if 13 - 13: OoOO0ooOOoo0O * OoOO * OOOo0
  if 55 - 55: OoOoOO00
  if 43 - 43: I1IiI - II111iiii + O0oO + o00O0oo
  if 17 - 17: OOooOOo
 def ok ( self , event = None ) :
  if 64 - 64: o00O0oo % II111iiii % ii
  if not self . validate ( ) :
   return
   if 3 - 3: oO0o0ooO0 + o0o00Oo0O
  self . on_ok ( )
  if 42 - 42: OoOO0ooOOoo0O / II111iiii + Ii - o00O0oo
  self . root . destroy ( )
  if 78 - 78: Ooo00oOo00o
 def cancel ( self , event = None ) :
  self . on_cancel ( )
  self . root . destroy ( )
  if 18 - 18: o0o00Oo0O - oO0o0ooO0 / oO0o0ooO0 + o0oO0 % o0oO0 - IIII
  if 62 - 62: oO0o0ooO0 - IIII - I1IiI % II111iiii / OoOO
  if 77 - 77: OoOoOO00 - OoOoOO00 . OOOo0 / OOooOOo
  if 14 - 14: o0000oOoOoO0o % o0o00Oo0O
 def validate ( self ) :
  return 1
  if 41 - 41: II111iiii + O0oO + OoOO0ooOOoo0O - IIII
 def on_ok ( self ) :
  pass
  if 77 - 77: Oo . IIII % o0oO0
 def on_cancel ( self ) :
  pass
  if 42 - 42: OoOO - II111iiii / Ii + OoOO0ooOOoo0O + Ooo00oOo00o
class iIi ( IiII1IiiIiI1 ) :
 def __init__ ( self , master , lang_table = iIIii1IIi ) :
  self . lang_table = iIIii1IIi
  IiII1IiiIiI1 . __init__ ( self , master , lang_table )
  if 40 - 40: OoOO . I1IiI . Oo . II111iiii
 def body ( self , master , root ) :
  root . title ( self . lang_table [ "TITLE_GET_PIN" ] , )
  iIiiiI1IiI1I1 = Frame ( master )
  Label ( iIiiiI1IiI1I1 , text = self . lang_table [ "PIN" ] ) . pack ( side = LEFT , padx = 5 , pady = 5 )
  self . pin = Entry ( iIiiiI1IiI1I1 , width = 50 , show = "*" )
  self . pin . bind ( "<Return>" , self . ok )
  self . pin . pack ( side = LEFT , padx = 5 , pady = 5 )
  iIiiiI1IiI1I1 . pack ( side = TOP , padx = 5 , pady = 5 )
  return self . pin
  if 33 - 33: o00O0oo + OoOoOO00 % Ii . o0oO0 - OOOo0
 def validate ( self ) :
  if self . pin . get ( ) == "" :
   tkMessageBox . showwarning (
 self . lang_table [ "TITLE_WARNING" ] ,
 self . lang_table [ "MSG_NO_BLANK_PIN" ]
 )
   self . pin . focus_set ( )
   return 0
  return 1
  if 66 - 66: o00O0oo - ii * ii . OoOO0ooOOoo0O . ii11ii1ii
 def on_ok ( self ) :
  self . result = ( 1 , self . pin . get ( ) )
  if 22 - 22: ii % o0000oOoOoO0o - oO0o0ooO0 . iI11I1II1I1I * Ii
 def on_cancel ( self , event = None ) :
  self . result = ( 0 , )
  if 32 - 32: Oo * o0o00Oo0O % OoOO % o00O0oo . IIII
  if 61 - 61: o0oO0
class oOOO00o ( IiII1IiiIiI1 ) :
 def __init__ ( self , master , lang_table = iIIii1IIi , dlg_type = 0 ) :
  self . dlg_type = dlg_type
  self . my_title = ""
  self . title_warning = lang_table [ "TITLE_WARNING" ]
  self . lang_table = iIIii1IIi
  if self . dlg_type == 2 :
   self . my_title = lang_table [ "TITLE_UNBLOCK_PIN" ]
   self . pin_label = lang_table [ "PUK" ]
   self . pin1_label = lang_table [ "NEW_PIN" ]
   self . pin2_label = lang_table [ "RETYPE_NEW_PIN" ]
   self . msg_no_blank_pin = lang_table [ "MSG_NO_BLANK_PUK" ]
   self . msg_no_blank_pin1 = lang_table [ "MSG_NO_BLANK_NEW_PIN" ]
   self . msg_new_pin_different = lang_table [ "MSG_NEW_PIN_DIFFERENT" ]
  elif self . dlg_type == 1 :
   self . my_title = lang_table [ "TITLE_CHANGE_PUK" ]
   self . pin_label = lang_table [ "OLD_PUK" ]
   self . pin1_label = lang_table [ "NEW_PUK" ]
   self . pin2_label = lang_table [ "RETYPE_NEW_PUK" ]
   self . msg_no_blank_pin = lang_table [ "MSG_NO_BLANK_OLD_PUK" ]
   self . msg_no_blank_pin1 = lang_table [ "MSG_NO_BLANK_NEW_PUK" ]
   self . msg_new_pin_different = lang_table [ "MSG_NEW_PUK_DIFFERENT" ]
  else :
   self . my_title = lang_table [ "TITLE_CHANGE_PIN" ]
   self . pin_label = lang_table [ "OLD_PIN" ]
   self . pin1_label = lang_table [ "NEW_PIN" ]
   self . pin2_label = lang_table [ "RETYPE_NEW_PIN" ]
   self . msg_no_blank_pin = lang_table [ "MSG_NO_BLANK_OLD_PIN" ]
   self . msg_no_blank_pin1 = lang_table [ "MSG_NO_BLANK_NEW_PIN" ]
   self . msg_new_pin_different = lang_table [ "MSG_NEW_PIN_DIFFERENT" ]
   if 97 - 97: o0000oOoOoO0o % o0000oOoOoO0o + OoOoOO00 * oO0o0ooO0
  IiII1IiiIiI1 . __init__ ( self , master , lang_table )
  if 54 - 54: o0000oOoOoO0o + IIII / oO0o0ooO0
 def body ( self , master , root ) :
  if 9 - 9: I1IiI / Oo - IIII . II111iiii / OOOo0 % IIII
  root . title ( self . my_title )
  iIiiiI1IiI1I1 = Frame ( master )
  Label ( iIiiiI1IiI1I1 , text = self . pin_label ) . grid ( row = 0 , sticky = W )
  if 71 - 71: O0oO . o0o00Oo0O
  self . pin = Entry ( iIiiiI1IiI1I1 , width = 50 , show = "*" )
  self . pin . grid ( row = 0 , column = 1 )
  if 73 - 73: OoOO0ooOOoo0O % I1IiI - o00O0oo
  Label ( iIiiiI1IiI1I1 , text = self . pin1_label ) . grid ( row = 1 , sticky = W )
  if 10 - 10: OOOo0 % ii11ii1ii
  self . pin1 = Entry ( iIiiiI1IiI1I1 , width = 50 , show = "*" )
  self . pin1 . grid ( row = 1 , column = 1 )
  if 48 - 48: o0000oOoOoO0o + o0000oOoOoO0o / OoOoOO00 / iI11I1II1I1I
  Label ( iIiiiI1IiI1I1 , text = self . pin2_label ) . grid ( row = 2 , sticky = W )
  if 20 - 20: OOooOOo
  self . pin2 = Entry ( iIiiiI1IiI1I1 , width = 50 , show = "*" )
  self . pin2 . grid ( row = 2 , column = 1 )
  if 77 - 77: I1IiI / o0000oOoOoO0o
  iIiiiI1IiI1I1 . pack ( )
  if 98 - 98: iI11I1II1I1I / II111iiii / Ii / OOooOOo
  self . pin . bind ( "<Return>" , self . ok )
  self . pin1 . bind ( "<Return>" , self . ok )
  self . pin2 . bind ( "<Return>" , self . ok )
  if 28 - 28: OoOO0ooOOoo0O - IIII . IIII + I1IiI - ii + o0o00Oo0O
  return self . pin
  if 95 - 95: Ooo00oOo00o % OoOO . o0o00Oo0O
 def validate ( self ) :
  if self . pin . get ( ) == "" :
   tkMessageBox . showwarning (
 self . title_warning ,
 self . msg_no_blank_pin
 )
   self . pin . focus_set ( )
   return 0
  if self . pin1 . get ( ) == "" :
   tkMessageBox . showwarning (
 self . title_warning ,
 self . msg_no_blank_pin1
 )
   self . pin1 . focus_set ( )
   return 0
  if self . pin1 . get ( ) != self . pin2 . get ( ) :
   tkMessageBox . showwarning (
 self . title_warning ,
 self . msg_new_pin_different
 )
   self . pin2 . focus_set ( )
   return 0
  return 1
  if 15 - 15: o0oO0 / o00O0oo . o00O0oo - II111iiii
 def on_ok ( self ) :
  self . result = ( 1 , self . pin . get ( ) , self . pin1 . get ( ) )
  if 53 - 53: IIII + OOOo0 * OoOO
 def on_cancel ( self , event = None ) :
  self . result = ( 0 , )
  if 61 - 61: II111iiii * OoOO0ooOOoo0O / ii . Ii . I1IiI
o00O = - 1
OOO0OOO00oo = 0
Iii111II = 1
iiii11I = 8
Ooo0OO0oOO = 9
ii11i1 = 10
IIIii1II1II = 11
i1I1iI = 16
oo0OooOOo0 = 17
o0O = 24
if 72 - 72: oO0o0ooO0 / II111iiii * Oo - O0oO
if 51 - 51: OoOoOO00 * Ooo00oOo00o % OOooOOo * OoOoOO00 % ii11ii1ii / o0oO0
def iIIIIii1 ( message_id , lang_table ) :
 if message_id == o00O :
  return 0
 elif message_id == OOO0OOO00oo :
  tkMessageBox . showinfo (
 lang_table [ "TITLE_INFORMATION" ] ,
 lang_table [ "MESSAGE_BOX_OPERATION_OK" ]
 )
  return 0
 elif message_id == Iii111II :
  tkMessageBox . showwarning (
 lang_table [ "TITLE_WARNING" ] ,
 lang_table [ "MESSAGE_BOX_OPERATION_FAILED" ]
 )
  return 0
 elif message_id == iiii11I :
  oo000OO00Oo = tkMessageBox . askquestion (
 lang_table [ "TITLE_WARNING" ] ,
 lang_table [ "MESSAGE_BOX_PIN_INCORRECT" ]
 )
  if tkMessageBox . YES == oo000OO00Oo :
   return 1
  return 0
 elif message_id == ii11i1 :
  tkMessageBox . showwarning (
 lang_table [ "TITLE_WARNING" ] ,
 lang_table [ "MESSAGE_BOX_PIN_DEACTIVATED" ]
 )
  return 0
 elif message_id == Ooo0OO0oOO :
  tkMessageBox . showwarning (
 lang_table [ "TITLE_WARNING" ] ,
 lang_table [ "MESSAGE_BOX_PIN_LOCKED" ]
 )
  return 0
 elif message_id == IIIii1II1II :
  tkMessageBox . showwarning (
 lang_table [ "TITLE_WARNING" ] ,
 lang_table [ "MESSAGE_BOX_PIN_LOCKED2" ]
 )
  return 0
 elif message_id == i1I1iI :
  oo000OO00Oo = tkMessageBox . askquestion (
 lang_table [ "TITLE_WARNING" ] ,
 lang_table [ "MESSAGE_BOX_PUK_INCORRECT" ]
 )
  if tkMessageBox . YES == oo000OO00Oo :
   return 1
  return 0
 elif message_id == oo0OooOOo0 :
  tkMessageBox . showwarning (
 lang_table [ "TITLE_WARNING" ] ,
 lang_table [ "MESSAGE_BOX_PUK_LOCKED" ]
 )
  return 0
 elif message_id == o0O :
  oo000OO00Oo = tkMessageBox . askquestion (
 lang_table [ "TITLE_INFORMATION" ] ,
 lang_table [ "MESSAGE_BOX_CONFIRM_SIGNATURE" ]
 )
  if tkMessageBox . YES == oo000OO00Oo :
   return 1
  return 0
 else :
  pass
 return 0
 if 51 - 51: IIII * OOooOOo + o0000oOoOoO0o + Ooo00oOo00o
def o0O0O00 ( ) :
 tkMessageBox . showinfo ( "Informazioni su Bit4id - Universal Middleware" , "Universal Middleware\n(c) 2007,2008 Bit4id srl (http://www.bit4id.com)" )
 if 86 - 86: o0000oOoOoO0o / IIII % Ii
 if 7 - 7: o0oO0 * Ooo00oOo00o % OoOO . IIII
def Ii1iIiII1ii1 ( dlgtype , message_id ) :
 if not oo000 :
  print >> sys . stderr , "tk_inter_module_present"
  return ( 0 , )
  if 62 - 62: iI11I1II1I1I * I1IiI
 if sys . platform != "darwin" and sys . platform != "win32" and os . getenv ( "DISPLAY" ) is None :
  print >> sys . stderr , "DISPLAY env var is:" , os . getenv ( "DISPLAY" )
  return ( 0 , )
  if 26 - 26: oO0o0ooO0 . O0oO
 oOOOOo0 = None
 iiII1i1 = Tk ( )
 try :
  iiII1i1 . tk . call ( "package" , "require" , "tile" )
 except :
  pass
  if 66 - 66: OoOO0ooOOoo0O - o0000oOoOoO0o
 I1i1III = Menu ( iiII1i1 )
 iiII1i1 . configure ( menu = I1i1III )
 if 63 - 63: OoOO0ooOOoo0O % OoOO * OoOO * Ooo00oOo00o / ii11ii1ii
 if iiII1i1 . tk . call ( "tk" , "windowingsystem" ) == "aqua" :
  o0ooO = Menu ( I1i1III , name = "apple" , title = "Bit4ID" )
  I1i1III . add_cascade ( label = "Universal Middleware" , menu = o0ooO )
  o0ooO . add_command ( label = "Informazioni su Universal Middleware" , command = o0O0O00 )
  O0o0O00Oo0o0 = Menu ( I1i1III , name = "bit4id" )
  I1i1III . add_cascade ( label = "Bit4ID" , menu = O0o0O00Oo0o0 )
  O0o0O00Oo0o0 . add_command ( label = "Informazioni su Universal Middleware" , command = o0O0O00 )
  if 87 - 87: o0oO0 * Oo % Ii % I1IiI - OoOO0ooOOoo0O
  if 68 - 68: O0oO % II111iiii . IIII . ii11ii1ii
 if dlgtype == "changepin" :
  oOOOOo0 = oOOO00o ( iiII1i1 , iIIii1IIi , 0 )
 elif dlgtype == "unblockpin" :
  oOOOOo0 = oOOO00o ( iiII1i1 , iIIii1IIi , 2 )
 elif dlgtype == "changepuk" :
  oOOOOo0 = oOOO00o ( iiII1i1 , iIIii1IIi , 1 )
 elif dlgtype == "message" :
  try :
   iiII1i1 . tk . call ( "console" , "hide" )
  except :
   pass
  iiII1i1 . geometry ( "0x0+0+0" )
  oo00 = iiII1i1
  oo00 . update_idletasks ( )
  o00 = oo00 . winfo_width ( )
  Oo0oO0ooo = oo00 . winfo_height ( )
  o0oOoO00o = oo00 . winfo_screenwidth ( ) - o00
  i1 = oo00 . winfo_screenheight ( ) - Oo0oO0ooo
  oo00 . geometry ( "%dx%d%+d%+d" % ( o00 , Oo0oO0ooo , o0oOoO00o / 2 , i1 / 4 ) )
  if 92 - 92: oO0o0ooO0 . O0oO
  if 31 - 31: O0oO . I1IiI / o0o00Oo0O
  o000O0o = ( iIIIIii1 ( message_id , iIIii1IIi ) , "" )
  iiII1i1 . destroy ( )
  iiII1i1 . mainloop ( )
  return o000O0o
 else :
  oOOOOo0 = iIi ( iiII1i1 , iIIii1IIi )
  if 42 - 42: I1IiI
 if not oOOOOo0 is None :
  try :
   iiII1i1 . tk . call ( "console" , "hide" )
  except :
   pass
  iiII1i1 . mainloop ( )
  iiII1i1 = None
  return oOOOOo0 . result
 else :
  return ( 0 , )
  if 41 - 41: Oo . o0oO0 + o0o00Oo0O * OOooOOo % Oo * Oo
if __name__ == '__main__' :
 iIIIIi1iiIi1 = ""
 iii1i1iiiiIi = - 1
 if len ( sys . argv ) > 1 :
  iIIIIi1iiIi1 = sys . argv [ 1 ] . lower ( )
 else :
  iIIIIi1iiIi1 = "getpin"
 if len ( sys . argv ) > 2 :
  iii1i1iiiiIi = int ( sys . argv [ 2 ] )
 oo000OO00Oo = Ii1iIiII1ii1 ( iIIIIi1iiIi1 , iii1i1iiiiIi )
 if 2 - 2: OOOo0 / o0o00Oo0O / OOooOOo % I1IiI % o00O0oo
 if oo000OO00Oo [ 0 ] == 0 :
  print "0"
 else :
  print "\t" . join ( [ str ( o0o00OO0 ) for o0o00OO0 in oo000OO00Oo ] )
  if 7 - 7: OoOO0ooOOoo0O + O0oO + o0o00Oo0O
# dd678faae9ac167bc83abf78e5cb2f3f0688d3a3
